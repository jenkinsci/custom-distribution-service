module.exports = {
    extends: [
        'react-app',
        'eslint:recommended',
        'plugin:react/recommended',
        'plugin:jsx-a11y/recommended',
    ],
    overrides: [
        {
            env: {
                jest: true
            },
            files: ['__tests__/**/*.js(x)?'],
            plugins: ['jest'],
        }
    ],
    parserOptions: {
        ecmaVersion: 2018,
        sourceType: 'module'
    },
    plugins: [
        'import',
        'jsx-a11y',
        'react',
        'eslint-plugin-no-inline-styles'
    ],
    rules: {
        'array-callback-return': 'error',
        eqeqeq: 'error',
        'import/first': 'error',
        indent: ['error', 4],
        'keyword-spacing': ['error', { after: true, before: true }],
        'no-console': 'warn',
        'no-eval': 'error',
        // 'no-inline-styles/no-inline-styles': ['error'],
        'no-new-object': 'error',
        'no-unused-vars': 'error',
        'no-var': ['error'],
        'prefer-template': ['error'],
        'quote-props': ['error', 'as-needed'],
        quotes: ['error', 'single'],
        'react/jsx-curly-spacing': ['error', {allowMultiline: true, when: 'always'}],
        'react/jsx-equals-spacing': ['error', 'never'],
        'react/no-unknown-property': ['error'],
        semi: ['error', 'never'],
        // 'sort-keys': ['error', 'asc', {caseSensitive: true, minKeys: 2, natural: false}],
    },

}
