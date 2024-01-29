/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx,vue}",
  ],
  theme: {
    extend: {
      colors: {
        'blue-dark': '#00435B',
        'blue-light': '#547C8C'
      },
    },
  },
  plugins: [],
}

