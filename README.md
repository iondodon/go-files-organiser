# Go Files Organiser Plugin for JetBrains IDEs

This plugin is designed to improve the organization of Go files within the Project View of JetBrains IDEs. When there's a Go file named `x.go` and another file named `xy.go` in the same directory, `x.go` becomes an expandable node that contains `xy.go` as a child. This way, related Go files are grouped together under a common parent, making the project structure cleaner and easier to navigate.

## Features

- Groups related Go files together in the Project View.
- Allows easy navigation and management of Go files.

## Installation

1. Download the latest release of the plugin from the [Releases](https://github.com/iondodon/go-files-organiser/releases) page.
2. Open your JetBrains IDE, and navigate to `Settings` -> `Installed Plugins`.
3. Click on `Install Plugin from Disk` and select the downloaded `.jar` file.
4. Restart the IDE to activate the plugin.

## Development

This project is developed using Kotlin and is built as a JetBrains IDE plugin.

### Building

- Open the project in IntelliJ IDEA.
- Build the project using the `Build` -> `Build Project` menu.

### Testing

Test the plugin in different environments using the `Run` -> `Run 'Plugin'` menu in IntelliJ IDEA.

## Contributing

Contributions are welcome! Feel free to open an issue or create a pull request on the [GitHub repository](https://github.com/iondodon/go-files-organiser).

## License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/iondodon/go-files-organiser/blob/main/LICENSE.txt) file for details.

## Support

If you encounter any issues or have any questions, please open an issue on GitHub.

---

Made with ❤️ by [Ion Dodon](https://github.com/iondodon)
