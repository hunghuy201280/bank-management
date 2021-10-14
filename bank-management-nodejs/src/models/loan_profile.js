const mongoose = require("mongoose");
const validator = require("validator");
const { ProofOfIncomeType } = require("../utils/enums");
const { toArray } = require("../utils/utils");
const loanProfileSchema = mongoose.Schema(
  {
    customer: {
      type: mongoose.Schema.Types.ObjectId,
      required: true,
      ref: "Customer",
    },
    staff: {
      type: mongoose.Schema.Types.ObjectId,
      required: true,
      ref: "Customer",
    },
    loanApplicationNumber: {
      type: String,
      required: true,
    },
    proofOfIncomeImg: {
      type: Buffer,
      required: true,
    },
    proofOfIncomeType: {
      type: Number,
      required: true,
      enum: [toArray(ProofOfIncomeType)],
    },
    moneyToLoan: {
      type: Number,
      required: true,
    },
    loanPurpose: {
      type: String,
      required: true,
    },
    loanDuration: {
      type: Number,
      required: true,
    },
    collateral: {
      type: String,
      required: true,
    },
    expectedSourceMoneyToRepay: {
      type: String,
      required: true,
    },
    benefitFromLoan: {
      type: String,
      required: true,
    },
    signatureImg: {
      type: Buffer,
      required: true,
    },
  },
  {
    timestamps: true,
  }
);

const LoanProfile = mongoose.model("LoanProfile", loanProfileSchema);

module.exports = LoanProfile;
