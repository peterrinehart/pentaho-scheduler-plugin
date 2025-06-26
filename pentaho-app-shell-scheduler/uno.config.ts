import { defineConfig } from "unocss";
import { theme } from "@hitachivantara/uikit-styles";
import { presetHv } from "@hitachivantara/uikit-uno-preset";

export default defineConfig({
  presets: [presetHv()],

  rules: [
    ["h-vh-header", { height: `calc(100vh - ${theme.header.height})` }],
    ["gap-xxs", { gap: theme.space.xxs }]
  ]
});
