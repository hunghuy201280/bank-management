const LoanProfile = require("../models/loan_profile");
const express = require("express");
const log = require("../utils/logger");
const auth = require("../middleware/auth");
const Customer = require("../models/customer");

const router = express.Router();

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

module.exports = router;
