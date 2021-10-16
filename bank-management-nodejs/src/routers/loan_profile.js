const LoanProfile = require("../models/loan_profile");
const express = require("express");
const log = require("../utils/logger");
const auth = require("../middleware/auth");
const Customer = require("../models/customer");

const router = express.Router();

//#region params
router.param("id", async (req, res, next, id) => {
  try {
    const loanProfile = await LoanProfile.findById(id);
    if (!loanProfile)
      return res
        .status(404)
        .send({ error: "This loan profile doesn't exist'" });
    req.loanProfile = loanProfile;
    next();
  } catch (err) {
    log.error(err);
    res.status(404).send({ error: "This loan profile doesn't exist'" });
  }
});
//#endregion

//#region Create Loan Profile

/**
 * Create Loan Profile
 */
router.post("/loan_profile", auth, async (req, res) => {
  const loanProf = req.body;
  try {
    if (loanProf.customer && loanProf.customerId)
      throw new Error("Provide only either customer or customerId!");

    //new customer
    if (loanProf.customerId == null) {
      if (loanProf.customer == null) throw new Error("No customer provided");
      const customer = new Customer(loanProf.customer);
      await customer.save();

      loanProf.customer = customer;
    } else {
      const customer = await Customer.findById(loanProf.customerId);
      if (!customer) throw new Error("Customer not found!");
      loanProf.customer = customer;
      delete loanProf.customerId;
    }
    loanProf.staff = req.staff._id;
    loanProf.loanApplicationNumber = await LoanProfile.getApplicationNumber();
    const loanProfile = new LoanProfile(loanProf);
    await loanProfile.save();
    res.send(loanProfile);
  } catch (error) {
    res.status(400).send({
      error: error.message,
    });
    log.error(error);
  }
});

//#endregion

/**
 * Get multiple loan profiles
 *
 */
router.get("/loan_profile", auth, async (req, res) => {
  try {
    let limit = 20,
      skip = 0;
    if (req.query.limit) {
      limit = parseInt(req.query.limit);
      if (limit == 0) limit = 20;
    }
    if (req.query.skip) {
      skip = parseInt(req.query.skip);
    }

    const match = {};
    const sort = {};
    if (req.query.sortBy) {
      const splittedSortQuery = req.query.sortBy.split(":");
      sort[splittedSortQuery[0]] = splittedSortQuery[1] === "desc" ? -1 : 1;
    }

    const result = await LoanProfile.find(match)
      .skip(skip)
      .limit(limit)
      .sort(sort)
      .exec();
    res.send(result);
  } catch (error) {
    log.error(error);
    res.status(400).send({ error });
  }
});

/**
 * Get single loan profile
 */
router.get("/loan_profile/:id", auth, async function (req, res) {
  res.send(req.loanProfile);
});

module.exports = router;
