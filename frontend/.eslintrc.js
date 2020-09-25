module.exports = {
  "env": {
    "browser": true,
    "es6": true
  },
  "extends": ["eslint:recommended", "react-app", "plugin:jsx-a11y/recommended"],
  "plugins": ["import","jsx-a11y"],
  "parserOptions": {
    "ecmaVersion": 2018,
    "sourceType": "module"
  },
  "rules": {
    "no-console": "warn",
    "no-eval": "error",
    "import/first": "error"
  },
  "overrides": [
    {
      "files": ["__tests__/**/*.js(x)?"],
      "plugins": ["jest"],
      "env": {
        "jest": true
      }
    }
  ]
}
