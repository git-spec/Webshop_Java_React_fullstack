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
    <div>
        <PragraphContainer>
            <Box component={'div'} 
                sx={{
                        width: '100%', 
                        height: '400px', 
                        backgroundImage: `url(${images[currentIndex]})`, 
                        backgroundRepeat: 'no-repeat', 
                        backgroundPosition: 'center', 
                        backgroundSize: 'cover'
                    }} 
            />
        </PragraphContainer>
        <PragraphContainer>
            <Stack direction="row" sx={{justifyContent: 'center'}} spacing={2}>
                {!!images.length && images.map((image, index) => {
                    return (
                        <Button
                            key={image + '_' + index} 
                            onClick={() => {setCurrentIndex(index)}} 
                            variant={'text'}
                            sx={{borderRadius: '50%'}}
                            disableRipple
                        >
                            <AvatarCaption 
                                name={image} 
                                image={image} 
                                size={'5rem'} 
                            />
                        </Button>
                    )
                })}
            </Stack>
        </PragraphContainer>
    </div>
  );
}