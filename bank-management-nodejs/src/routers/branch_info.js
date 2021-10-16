const express = require("express");
const BranchInfo = require("../models/branch_info");
const auth = require("../middleware/auth");
const log = require("../utils/logger");
const { StaffRole } = require("../utils/enums");

const router = express.Router();

//#region create branch

router.post("/branch_info", auth, async (req, res) => {
  try {
    if (req.staff.role != StaffRole.Director)
      return res.send({
        error: "Invalid permission",
      });
    const branchInfo = new BranchInfo(req.body);
    await branchInfo.save();
    res.send(branchInfo);
  } catch (error) {
    log.error(error);
    res.status(400).send({ error });
  }
});

//#endregion

module.exports = router;
