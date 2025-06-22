import * as React from 'react';
import { responsiveFontSizes, styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

type Props<T> = {
    header: string[];
    content: Array<string | number>[];
};

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: 'transparent'
  },
  [`&.${tableCellClasses.body}`]: {
    
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: 'transparent',
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

export default function TableBasic<T>({content, header}: Readonly<Props<T>>) {
  return (
    <TableContainer component={Paper} elevation={0}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead sx={{ backgroundColor: 'transparent' }}>
          <TableRow>
            {header.map((el, i) => {
                if (i <= 1) {
                    return <StyledTableCell key={`${el[i]}_${i}`} sx={{px: 0, pb: '.3rem', fontSize: '1rem'}}>{el}</StyledTableCell> 
                } else {
                    return <StyledTableCell align="right" key={`${el[i]}_${i}`} sx={{px: 0, pb: '.3rem', fontSize: '1rem' }}>{el}</StyledTableCell>
                }
            })}
          </TableRow>
        </TableHead>
        <TableBody>
            {content.map((el, i) => {
                return (
                    <StyledTableRow key={`${el[i]}_${i}`}>
                        {
                            el.map((val, x) => {
                                if (x <= 1) {
                                return <StyledTableCell key={`${val}_${x}`} component="th" scope="row" sx={{fontSize: '1rem', px: 0}}>
                                        {val}
                                    </StyledTableCell>

                                } else {
                                    return <StyledTableCell key={`${val}_${x}`} align="right" sx={{fontSize: '1rem', px: 0}}>
                                        {val}
                                    </StyledTableCell>
                                }
                            })
                        }
                    </StyledTableRow>
                ) 
            })}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
