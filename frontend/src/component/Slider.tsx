import { useState } from 'react';
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';

import AvatarCaption from './AvatarCaption';
import Button from '@mui/material/Button';

type Props = {
    images: string[];
};

export default function Slider({images}: Readonly<Props>) {
  const [currentIndex, setCurrentIndex] = useState(0);

  return (
    <Stack sx={{alignItems: 'center'}}>
        <Box 
            component={'div'} 
            width={'100%'} 
            height={'fit-content'} 
            sx={
                    {
                        boxShadow: '0px 1px 3px rgba(0,0,0,0.2), 0px 1px 1px rgba(0,0,0,0.14), 0px 2px 1px rgba(0,0,0,0.12)', 
                        mb: 4,
                        display: 'flex',
                        justifyContent: 'center'
                    }
                }
            >
            <Box component={'div'} 
                sx={{
                        width: {sm: '100%', md: '80%', lg: '80%'}, 
                        height: '400px', 
                        backgroundImage: `url(${images[currentIndex]})`, 
                        backgroundRepeat: 'no-repeat', 
                        backgroundPosition: 'center', 
                        backgroundSize: 'cover', 
                                    // boxShadow: '0px 1px 3px rgba(0,0,0,0.2), 0px 1px 1px rgba(0,0,0,0.14), 0px 2px 1px rgba(0,0,0,0.12)',
                        // mb: 4
                    }} 
            />
        </Box>
        <Stack direction="row" sx={{justifyContent: 'center'}} spacing={2}>
            {!!images.length && images.map((image, index) => {
                return (
                    <Button
                        key={image + '_' + index} 
                        onClick={() => {setCurrentIndex(index)}} 
                        sx={
                            {
                                borderRadius: '50%', 
                                boxShadow: '0px 1px 3px rgba(0,0,0,0.2), 0px 1px 1px rgba(0,0,0,0.14), 0px 2px 1px rgba(0,0,0,0.12)',
                                // border: '1px solid #d7b86f',
                                '&:hover': {transform: 'scale(110%)'},
                                '&': {
                                    minWidth: 'fit-content',
                                    minHeight: 'fit-content',
                                    padding: 0
                                }
                            }
                        }
                        disableRipple
                    >
                        <AvatarCaption 
                            name={image} 
                            image={image} 
                            size={'3.25rem'} 
                        />
                    </Button>
                )
            })}
        </Stack>
    </Stack>
  );
}