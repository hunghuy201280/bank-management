const User = require("../models/user");
const express = require("express");
const log = require("../utils/logger");

const router = express.Router();

/**
 * Register user
 */
router.post("/users", async (req, res) => {
  const user = new User(req.body);
  try {
    const token = await user.getToken();
    res.status(201).send({ user, token });
  } catch (err) {
    log.error(err);
    res.sendStatus(400).send();
  }
});

router.post("/users/login", async (req, res) => {
  try {
    const user = await User.findByCredentials(
      req.body.email,
      req.body.password
    );
    const token = await user.getToken();
    res.send({ user, token });
  } catch (err) {
    log.error(err);
    res.sendStatus(400).send();
  }
});

module.exports = router;
