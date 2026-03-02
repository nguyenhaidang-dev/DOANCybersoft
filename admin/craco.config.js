const path = require('path');

module.exports = {
  webpack: {
    configure: (webpackConfig) => {
      // Fix: css-loader + webpack5 generates __webpack_exports__.d.e.f.a.u.l.t
      const traverseRules = (rules) => {
        if (!rules) return;
        rules.forEach((rule) => {
          if (rule.use && Array.isArray(rule.use)) {
            rule.use.forEach((use) => {
              if (
                use.loader &&
                use.loader.includes('css-loader') &&
                !use.loader.includes('postcss') &&
                use.options
              ) {
                use.options.esModule = false;
              }
            });
          }
          if (rule.oneOf) traverseRules(rule.oneOf);
          if (rule.rules) traverseRules(rule.rules);
        });
      };
      traverseRules(webpackConfig.module.rules);
      return webpackConfig;
    },
  },
};
