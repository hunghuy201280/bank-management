const express = require("express");
require("./db/mongoose");
const staffRouter = require("./routers/staff");

const app = express();

app.use(express.json());

app.use(staffRouter);

module.exports = app;
