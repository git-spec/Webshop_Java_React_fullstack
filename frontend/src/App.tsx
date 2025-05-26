import './App.css';
import CssBaseline from '@mui/material/CssBaseline';
import {ThemeProvider} from '@mui/material/styles';

import {globalTheme} from "./themes/globalTheme.ts";


function App() {

  return (
    <>
      <ThemeProvider theme={globalTheme}>
          <CssBaseline>
              <p style={{textAlign: 'center', marginTop: '8rem', fontFamily: 'SourceSerif4', fontWeight: 200, fontStyle: 'normal', fontSize: 24, letterSpacing: '.03em'}}>
                  Hello World!
              </p>
          </CssBaseline>
      </ThemeProvider>
    </>
  )
}

export default App
