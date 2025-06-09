import { useEffect, useState } from "react";
import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";

import type { IColor } from "@/interface/IColor";

interface IProps extends IColor {
    select?: boolean;
    click?: (color: string) => void;
};


export default function AvatarCaption({name, image, caption, size, filter, select, click}: Readonly<IProps>) {
    const template = (
        <>
            <Avatar 
                alt={name} 
                src={image} 
                sx={
                    {
                        filter: `brightness(${filter?.bright ?? 1}) grayscale(${filter?.flat ?? 0})`, 
                        width: `${size ?? ''}`, 
                        height: `${size ?? ''}`,
                        transform: `scale(${select ? '120%' : '100%'})`
                    }
                } 
            />
            <Typography variant={'caption'} sx={{fontStyle: 'italic', pt: '.25rem'}}>{caption}</Typography>
        </>
    );

    return (
        <Stack sx={{alignItems: 'center'}}>
            {
                click ? 
                    <Button
                        onClick={() => {click(name)}} 
                        sx={
                            {
                                color: 'black',
                                textTransform: 'none',flexDirection: 'column',
                                '&:hover': {backgroundColor: 'unset'}
                            }
                        }
                        disableRipple
                    >
                        {template}
                    </Button>
                :
                    template
            }
        </Stack>
    );
}