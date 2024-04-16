const CompressionPlugin = require('compression-webpack-plugin');

module.exports = {
    devServer: {
        host: '0.0.0.0',
        port: 9090,
        proxy: {
            '/c': {
                target: 'http://127.0.0.1:9090',
                ws: true
            }
        }
    },
    productionSourceMap: false,
    configureWebpack: {
        plugins: [
            new CompressionPlugin({
                test: /\.js$|\.html$|\.css/,
                threshold: 1024
            })
        ]
    }
}