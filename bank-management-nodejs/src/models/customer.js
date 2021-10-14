const mongoose = require("mongoose");
const validator = require("validator");

const customerSchema = mongoose.Schema(
  {
    name: {
      type: String,
      required: true,
      trim: true,
    },
    dateOfBirth: Date,
    address: {
      trim: true,
      type: String,
    },
    identityNumber: {
      trim: true,
      type: String,
    },
    identityCardCreatedDate: Date,
    phoneNumber: {
      trim: true,
      type: String,
    },
    permanentResidence: {
      trim: true,
      type: String,
    },
    email: {
      type: String,
      unique: true,
      trim: true,
      lowercase: true,
      validate(value) {
        return validator.isEmail(value);
      },
    },
  },
  {
    timestamps: true,
  }
);

const Customer = mongoose.model("Customer", customerSchema);

module.exports = Customer;
