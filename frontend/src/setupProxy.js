const { createProxyMiddleware } = require('http-proxy-middleware')
module.exports = function(app) {
    app.use(
        '/api',
        createProxyMiddleware({
            changeOrigin: true,
            target: process.env.DEV_API_SERVER_PROXY || 'https://customize.jenkins.io',
        })
    )
}
