/** @type {import('tailwindcss').Config} */
module.exports = {
	content: ["../resources/templates/**/*.{html,js}"],
	theme: {
		extend: {
			colors: {
				"form-control": "rgb(249,250,251)",
			},
		},
	},
	plugins: [require("@tailwindcss/forms"), require("flowbite/plugin")],
};
