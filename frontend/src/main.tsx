import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import CssBaseline from '@mui/material/CssBaseline';
import {ThemeProvider} from '@mui/material/styles';

import './index.css';
import App from './App.tsx';
import {globalTheme} from "./themes/globalTheme.ts";
import { BrowserRouter } from 'react-router-dom';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <ThemeProvider theme={globalTheme}>
          <CssBaseline>
            <BrowserRouter>
              <App />
            </BrowserRouter>
          </CssBaseline>
      </ThemeProvider>
  </StrictMode>,
)
