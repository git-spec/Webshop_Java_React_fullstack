import { useState } from 'react';
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';

import PragraphContainer from './ParagraphContainer';
import AvatarCaption from './AvatarCaption';
import Button from '@mui/material/Button';

type Props = {
    images: string[];
};

export default function Slider({images}: Readonly<Props>) {
  const [currentIndex, setCurrentIndex] = useState(0);

  return (
    <Stack sx={{alignItems: 'center'}}>
            <Box component={'div'} 
                sx={{
                        width: {sm: '100%', md: '80%', lg: '70%'}, 
                        height: '400px', 
                        backgroundImage: `url(${images[currentIndex]})`, 
                        backgroundRepeat: 'no-repeat', 
                        backgroundPosition: 'center', 
                        backgroundSize: 'cover',
                        mb: 4
                    }} 
            />
            <Stack direction="row" sx={{justifyContent: 'center'}} spacing={2}>
                {!!images.length && images.map((image, index) => {
                    return (
                        <Button
                            key={image + '_' + index} 
                            onClick={() => {setCurrentIndex(index)}} 
                            sx={
                                {
                                    borderRadius: '50%', 
                                    border: '1px solid #d7b86f',
                                    '&:hover': {backgroundColor: 'rgba(215, 184, 111, 0.1)'}
                                }
                            }
                            disableRipple
                        >
                            <AvatarCaption 
                                name={image} 
                                image={image} 
                                size={'4rem'} 
                            />
                        </Button>
                    )
                })}
            </Stack>
    </Stack>
  );
}