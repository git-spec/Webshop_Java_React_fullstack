import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import CssBaseline from '@mui/material/CssBaseline';
import {ThemeProvider} from '@mui/material/styles';

import './index.css';
import App from './App.tsx';
import {globalTheme} from "./theme/globalTheme.ts";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <ThemeProvider theme={globalTheme}>
          <CssBaseline>
              <App />
          </CssBaseline>
      </ThemeProvider>
  </StrictMode>,
)
