/** @type {import('tailwindcss').Config} */
module.exports = {
	content: ["../resources/templates/**/*.{html,js}"], // it will be explained later
	theme: {
		extend: {
			colors: {
				brand: "3fbaeb",
				dark: "0c87b8",
				DEFAULT: "0fa9e6",
			},
		},
	},
	plugins: [],
};
