const mongoose = require("mongoose");
const validator = require("validator");
const { dateSetter, dateGetter } = require("../utils/utils");

const customerSchema = mongoose.Schema(
  {
    name: {
      type: String,
      required: true,
      trim: true,
    },
    dateOfBirth: {
      type: Date,
      required: true,
      set: dateSetter,
      get: dateGetter,
    },
    address: {
      trim: true,
      required: true,
      type: String,
    },
    identityNumber: {
      trim: true,
      required: true,
      unique: true,
      type: String,
      validate(value) {
        return validator.isNumeric(value, {
          no_symbols: true,
        });
      },
    },
    identityCardCreatedDate: {
      type: Date,
      set: dateSetter,
      required: true,
      get: dateGetter,
    },
    phoneNumber: {
      trim: true,
      unique: true,
      required: true,
      type: String,
      validate(value) {
        return validator.isNumeric(value, {
          no_symbols: true,
        });
      },
    },
    permanentResidence: {
      trim: true,
      required: true,
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
      sparse: true,
      index: true,
    },
  },
  {
    timestamps: true,
  }
);
// customerSchema.methods.toJSON = function () {
//   const customerObject = this.toObject();
//   customerObject.dateOfBirth = dateGetter(customerObject.dateOfBirth);
//   customerObject.identityCardCreatedDate = dateGetter(
//     customerObject.identityCardCreatedDate
//   );

//   return customerObject;
// };

const Customer = mongoose.model("Customer", customerSchema);

module.exports = Customer;
