const mongoose = require("mongoose");
const validator = require("validator");
const { ProofOfIncomeType } = require("../utils/enums");
const { toArray } = require("../utils/utils");
const moment = require("moment");

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
    proofOfIncome: [
      {
        imageId: {
          type: String,
          required: true,
        },
        imageType: {
          type: Number,
          required: true,
          enum: [toArray(ProofOfIncomeType)],
        },
      },
    ],
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
      type: String,
      required: true,
    },
  },
  {
    timestamps: true,
  }
);

loanProfileSchema.statics.getApplicationNumber = async function () {
  const today = moment().startOf("day");

  const num = await LoanProfile.count({
    createdAt: {
      $gte: today.toDate(),
      $lte: moment(today).endOf("day").toDate(),
    },
  });
  return `HSSV.${today.year()}.${today.date()}${today.month() + 1}.${num + 1}`;
};

const LoanProfile = mongoose.model("LoanProfile", loanProfileSchema);

module.exports = LoanProfile;
