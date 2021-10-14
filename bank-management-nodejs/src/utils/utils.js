function toArray(obj) {
  return Object.keys(obj).map(function (key) {
    return obj[key];
  });
}

module.exports = {
  toArray,
};
