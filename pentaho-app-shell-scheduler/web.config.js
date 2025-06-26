import eslint from "@eslint/js";
import vitest from "@vitest/eslint-plugin";
import eslintConfigPrettier from "eslint-config-prettier";
import pluginReact from "eslint-plugin-react";
import * as reactHooks from "eslint-plugin-react-hooks";
import pluginJsxA11y from "eslint-plugin-jsx-a11y";
import tseslint from "typescript-eslint";

export default tseslint.config(
  eslintConfigPrettier,
  eslint.configs.recommended,
  tseslint.configs.eslintRecommended,
  tseslint.configs.recommendedTypeChecked,
  reactHooks.configs["recommended-latest"],
  {
    plugins: {
      "jsx-a11y": pluginJsxA11y,
    },
    rules: pluginJsxA11y.configs.recommended.rules,
  },
  {
    ...pluginReact.configs.flat.recommended,
    settings: {
      react: {
        version: "detect"
      }
    }
  },
  {
    ignores: ["src/api/*"]
  },
  {
    files: ["**/tests/**"],
    extends: [vitest.configs.recommended],
    rules: {
      // @see the Note on https://react.dev/reference/react/act#await-act-async-actfn.
      "@typescript-eslint/require-await": "off",
      // some expects are inside a common function which this does not detect
      "vitest/expect-expect": "off"
    }
  },
  {
    languageOptions: {
      parserOptions: {
        projectService: true,
        tsconfigDirName: import.meta.dirname
      }
    }
  },
  {
    rules: {
      "jsx-a11y/no-autofocus": "off",
      "react/react-in-jsx-scope": "off",
      "@typescript-eslint/no-floating-promises": "off",
      "@typescript-eslint/no-misused-promises": "off",
      "react/prop-types": "off",
      "@typescript-eslint/no-unused-vars": [
        "error",
        {
          args: "all",
          argsIgnorePattern: "^_",
          caughtErrors: "all",
          caughtErrorsIgnorePattern: "^_",
          destructuredArrayIgnorePattern: "^_",
          varsIgnorePattern: "^_",
          ignoreRestSiblings: true
        }
      ]
    }
  }
);
