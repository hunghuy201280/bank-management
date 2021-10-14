const mongoose = require("mongoose");

const BImage = mongoose.model("BImage", {
  data: { type: Buffer, required: true },
});

module.exports = BImage;
