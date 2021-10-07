const mongoose = require("mongoose");
const validator = require("validator");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

const userSchema = mongoose.Schema({
  name: {
    type: String,
    required: true,
    trim: true,
  },
  password: {
    type: String,
    required: true,
    trim: true,
    validate(value) {
      return validator.isStrongPassword(value, {
        minSymbols: 0,
      });
    },
  },
  email: {
    type: String,
    required: true,
    unique: true,
    trim: true,
    lowercase: true,
    validate(value) {
      return validator.isEmail(value);
    },
  },
  tokens: [
    {
      token: {
        type: String,
        required: true,
      },
    },
  ],
});

/**
 * Remove password and token from User model
 * before send it back
 *
 */
userSchema.methods.toJSON = function () {
  const userObject = this.toObject();
  delete userObject.password;
  delete userObject.tokens;
  return userObject;
};

/**
 *
 * @param {String} email
 * @param {String} password - password in plain text
 * @returns {User} user - user object if there is an user with correspond email and password
 *
 */
userSchema.statics.findByCredentials = async function (email, password) {
  const user = await User.findOne({ email });
  if (!user) {
    throw new Error("This user does not exist");
  }

  const isMatchPassword = await bcrypt.compare(password, user.password);
  if (!isMatchPassword) {
    throw new Error("Password is not correct");
  }
  return user;
};

/**
 * Generate new token for user
 * @returns {String} token -The token generated
 */
userSchema.methods.getToken = async function () {
  const user = this;
  const token = jwt.sign(
    {
      _id: user._id.toString(),
    },
    process.env.JWT_SECRET_KEY
  );
  user.tokens.push({ token });
  await user.save();
  return token;
};

/**
 * Hash the password before save
 * only hash if password is modified
 */
userSchema.pre("save", async function (next) {
  const user = this;
  if (user.isModified("password")) {
    user.password = await bcrypt.hash(user.password, 8);
  }
  next();
});

const User = mongoose.model("User", userSchema);

module.exports = User;
