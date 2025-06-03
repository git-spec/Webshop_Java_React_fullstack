import { createTheme } from '@mui/material/styles';

export const headerTheme = createTheme({
    typography: {
        fontFamily: 'SourceSans3',
    },
    components: {
      MuiAppBar: {
        styleOverrides: {
          root: {
            backgroundColor: 'white',
            color: 'black',
            borderBottom: '2px solid #D7B76F',
            boxShadow: '1px 2px 6px rgba(0, 0, 0, .2)'
          }
        }
      },
      MuiListItemText: {
        styleOverrides: {
          root: {
            color: 'black', 
            fontFamily: 'SourceSans3',
          },
          primary: { 
            fontSize: '1.3rem'
          }
        }
      }
    },
});