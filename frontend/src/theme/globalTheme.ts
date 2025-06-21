import { createTheme } from '@mui/material/styles';

export const globalTheme = createTheme({
    typography: {
        fontFamily: 'SourceSerif4, SourceSans3',
    },
    palette: {
      primary: {
        main: '#D7B76F',
      },
    },
    components: {
      //   MuiCssBaseline: {
      //       styleOverrides: `
      //   p {
      //     color: grey;
      //   }
      // `,
      //   },
    },
});