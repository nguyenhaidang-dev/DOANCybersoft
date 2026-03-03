const path = require('path');

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
      '/ai': {
        target: 'http://localhost:5000',
        changeOrigin: true,
        pathRewrite: { '^/ai': '' },
      },
    },
  },
  webpack: {
    configure: (webpackConfig) => {
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
