const express = required("express");
require("./db/mongoose");
const app = express();

app.use(express.json());

module.exports = app;
