const sdkPath = process.env.ANDROID_HOME || process.env.ANDROID_SDK_ROOT || '/Users/vandersonoliveira/Library/Android/sdk';
process.env.ANDROID_HOME = sdkPath;
process.env.ANDROID_SDK_ROOT = sdkPath;

exports.config = {
    runner: 'local',
    port: 4723,
    specs: ['./test/specs/**/*.js'],
    exclude: [],
    maxInstances: 1,

    capabilities: [
        {
            // --- 🤖 CONFIGURAÇÃO ANDROID ---
            platformName: 'Android',
            'appium:automationName': 'UiAutomator2',
            'appium:deviceName': 'Pixel 7', 
            'appium:app': './apps/android-demo.apk',
            'appium:adbExecTimeout': 60000,
            'appium:noSign': true // <--- ESSA É A MÁGICA QUE PULA O JAVA
        }
    ],

    logLevel: 'info',
    services: ['appium'],
    framework: 'mocha',
    
    // --- 📊 CONFIGURAÇÃO DOS RELATÓRIOS (Spec + Allure) ---
    reporters: ['spec', ['allure', {
        outputDir: 'allure-results',
        disableWebdriverStepsReporting: true,
        disableWebdriverScreenshotsReporting: false, // Isso já está perfeito!
    }]],

    mochaOpts: {
        ui: 'bdd',
        timeout: 60000
    },

    // --- 📸 HOOKS PARA CAPTURA AUTOMÁTICA DE EVIDÊNCIAS ---
    afterTest: async function (test, context, { error, result, duration, passed, retries }) {
        // Se o teste falhar (!passed), o Appium tira a foto e o Allure anexa automaticamente
        if (!passed) {
            await browser.takeScreenshot();
            console.log(`📸 Screenshot capturado para a falha no teste: ${test.title}`);
        }
    }
};