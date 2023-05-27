import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '^/api/.*': {
        target: loadEnv('', process.cwd()).VITE_DEV_BACKEND_URL,
        changeOrigin: true,
      }
    }
  },
  build: {
    outDir: '../server/src/main/resources/client',
    assetsDir: './assets',
  },
  base: '/',
})
