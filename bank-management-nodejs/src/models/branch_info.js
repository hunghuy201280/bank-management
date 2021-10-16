const mongoose = require("mongoose");
const validator = require("validator");
const branchInfoSchema = mongoose.Schema({
  branchAddress: {
    type: String,
    required: true,
  },
  branchPhoneNumber: {
    type: String,
    required: true,
    validate(value) {
      return validator.isNumeric(value, {
        no_symbols: true,
      });
    },
  },
  branchFax: {
    type: String,
    required: true,
  },
  branchCode: {
    type: String,
    required: true,
  },
});

const BranchInfo = mongoose.model("BranchInfo", branchInfoSchema);
module.exports = BranchInfo;
