const Staff = require("../models/staff");
const express = require("express");
const log = require("../utils/logger");

const router = express.Router();

/**
 * Register staff
 */
router.post("/staffs", async (req, res) => {
  const staff = new Staff(req.body);
  try {
    const token = await staff.getToken();
    res.status(201).send({ staff, token });
  } catch (err) {
    log.error(err);
    res.status(400).send({ error: err });
  }
});

router.post("/staffs/login", async (req, res) => {
  try {
    const staff = await Staff.findByCredentials(
      req.body.email,
      req.body.password
    );
    const token = await staff.getToken();
    res.send({ staff, token });
  } catch (err) {
    log.error(err);
    res.sendStatus(400).send();
  }
});

module.exports = router;
