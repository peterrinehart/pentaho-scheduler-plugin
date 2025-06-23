import type { HvAppShellConfig } from "@hitachivantara/app-shell-vite-plugin";

export default (): HvAppShellConfig => ({
  name: "pas-scheduler",

  baseUrl: "/",

  header: {
    actions: [
      { bundle: "@hv/help-client/button.js",
        config: { url: "https://www.hitachivantara.com/", description: "Hitachi Vantara Help Link" }
      },
      { bundle: "@hv/theming-client/colorModeSwitcher.js"      },
      { bundle: "@hv/app-switcher-client/toggle.js",
        config: { title: "Apps",
									apps: [
										{ label: "App 1", description: "Application 1", url: "#", target: "NEW" },
										{ label: "App 2", description: "Application 2", url: "#", target: "SELF", icon: { iconType: "uikit", name: "Warehouse" } }
									]}
      }
    ]
  },

  mainPanel: {
      "views": [
        {
          "bundle": "@self/pages/Example.js",
          "route": "/scheduler"
        }
      ],
    maxWidth: "xl"  },

});
