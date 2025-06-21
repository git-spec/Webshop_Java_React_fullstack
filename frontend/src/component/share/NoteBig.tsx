import Typography from "@mui/material/Typography";

type Props = {
    value: string;
};


export default function NoteBig({value}: Readonly<Props>) {

    return (
        <Typography 
            variant="h1" 
            textAlign={'center'} 
            fontFamily={'SourceSans3'} 
            fontWeight={500} fontSize={'2rem'} 
            color="lightgray"
            marginTop={12}
        >
            {value}
        </Typography>
    );
}