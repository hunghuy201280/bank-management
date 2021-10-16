const express = require("express");
require("./db/mongoose");
const staffRouter = require("./routers/staff");
const bImageRouter = require("./routers/b_image");
const loanProfileRouter = require("./routers/loan_profile");
const branchInfoRouter = require("./routers/branch_info");
const loanContractRouter = require("./routers/loan_contract");
const app = express();

app.use(express.json());

app.use(staffRouter);
app.use(bImageRouter);
app.use(loanProfileRouter);
app.use(branchInfoRouter);
app.use(loanContractRouter);

module.exports = app;
