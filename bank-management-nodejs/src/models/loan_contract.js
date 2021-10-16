const mongoose = require("mongoose");
const validator = require("validator");
const { LoanType } = require("../utils/enums");
const { toArray } = require("../utils/utils");

const loanContractSchema = mongoose.Schema(
  {
    branchInfo: {
      type: mongoose.Schema.Types.ObjectId,
      required: true,
      ref: "BranchInfo",
    },
    loanProfile: {
      type: mongoose.Schema.Types.ObjectId,
      required: true,
      ref: "LoanProfile",
    },
    commitment: {
      type: String,
      required: true,
    },
    loanType: {
      type: Number,
      required: true,
      enum: [toArray(LoanType)],
    },
  },
  {
    timestamps: true,
  }
);

const LoanContract = mongoose.model("LoanContract", loanContractSchema);
module.exports = LoanContract;
