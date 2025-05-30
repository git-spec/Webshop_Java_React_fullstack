import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api' : {
        target: "http://localhost:8080"
      }
    }
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
      '@data': path.resolve(__dirname, './data'),
      // Weitere Aliase nach Bedarf
    }
  }
})
