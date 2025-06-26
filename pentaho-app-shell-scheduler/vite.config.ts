/// <reference types="vite/client" />
/// <reference types="vitest" />

import { dirname, resolve } from "node:path";
import { fileURLToPath } from "node:url";
import react from "@vitejs/plugin-react";
import unoCSS from "unocss/vite";
import { mergeConfig, type UserConfig } from "vite";
import cssInjectedByJsPlugin from "vite-plugin-css-injected-by-js";
import { presetHv } from "@hitachivantara/uikit-uno-preset";

const __dirname = dirname(fileURLToPath(import.meta.url));

/** minimum base configuration shared across all packages */
export const baseConfig: UserConfig = {
  plugins: [
    react(),
    unoCSS({ mode: "per-module", presets: [presetHv()] }),
    cssInjectedByJsPlugin({ relativeCSSInjection: true })
  ],

  server: {
    proxy: {
      "/pentaho": {
        target: "http://localhost:8080",
        changeOrigin: true
      }
    }
  },

  test: {
    globals: true,
    watch: false,
    environment: "happy-dom",
    setupFiles: resolve(__dirname, ".config/setupTests.ts"),
    coverage: {
      provider: "v8",
      reporter: ["lcov", "text"],
      exclude: [
        "vite.config.ts",
        "openapi-codegen.config.ts",
        "coverage/**",
        "dist/**",
        "**/test{,s}/**",
        "**/index.ts",
        "**/styles.ts",
        "**/*.d.ts",
        "src/assembly/**",
        "src/types/**",
        "src/api/**"
      ]
    }
  }
};

export default mergeConfig(baseConfig, {
  build: {
    rollupOptions: {
      external: ["@pentaho-apps/pas-scheduler-parent"]
    }
  }
});
