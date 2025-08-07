import { useState, type ChangeEvent } from "react";
import Box from "@mui/material/Box";
import FormControl from "@mui/material/FormControl";
import Grid from "@mui/material/Grid";
// import InputAdornment from "@mui/material/InputAdornment";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
// import OutlinedInput from "@mui/material/OutlinedInput";
import Select, { type SelectChangeEvent } from "@mui/material/Select";
import TextField from "@mui/material/TextField";
// import FormHelperText from "@mui/material/FormHelperText";

// function MyFormHelperText() {
//     const { focused } = useFormControl() || {};

//     const helperText = useMemo(() => {
//         if (focused) {
//             return 'Geben Sie die Zutaten getrennt mit einem Komma ein.';
//         }

//         return ' ';
//     }, [focused]);

//     return <FormHelperText>{helperText}</FormHelperText>;
// }

type Props = {
    props?: {
        gender?: string;
        firstname: string;
        lastname: string;
        street?: string;
        homenumber?: string;
        postalcode?: string;
        domicile?: string;
    }
};


export default function PersonDetailsForm({props}: Readonly<Props>) {
    const [gender, setGender] = useState<string>(props?.gender ?? '');
    const [firstname, setFirstname] = useState<string>(props?.firstname ?? '');
    const [lastname, setLastname] = useState<string>(props?.lastname ?? '');
    const [street, setStreet] = useState<string>(props?.street ?? '');
    const [homenumber, setHomenumber] = useState<string>(props?.homenumber ?? '');
    const [postalcode, setPostalcode] = useState<string>(props?.postalcode ?? '');
    const [domicile, setDomicile] = useState<string>(props?.domicile ?? '');

    function handleGender(e: SelectChangeEvent) {
        setGender(e.target.value);
    }

    function handleFirstname(e: ChangeEvent<HTMLInputElement>) {
        setFirstname(e.target.value);
    }

    function handleLastname(e: ChangeEvent<HTMLInputElement>) {
        setLastname(e.target.value);
    }

    function handleStreet(e: ChangeEvent<HTMLInputElement>) {
        setStreet(e.target.value);
    }

    function handleHomenumber(e: ChangeEvent<HTMLInputElement>) {
        setHomenumber(e.target.value);
    }

    function handlePostalcode(e: ChangeEvent<HTMLInputElement>) {
        setPostalcode(e.target.value);
    }

    function handleDomicile(e: ChangeEvent<HTMLInputElement>) {
        setDomicile(e.target.value);
    }

    return (
        <Grid container columnSpacing={2} flexDirection={'column'}>
            <Grid size={3} sx={{mb: 2}}>
                <Box sx={{ minWidth: 100 }}>
                    <FormControl fullWidth>
                        <InputLabel id={'gender-label'} size="small">Gender</InputLabel>
                        <Select
                            id={'gender'}
                            labelId={'gender-label'}
                            label={'Geschlecht'}
                            size="small"
                            value={gender}
                            onChange={handleGender}
                        >
                            <MenuItem value={'MALE'}>männlich</MenuItem>
                            <MenuItem value={'FEMALE'}>weiblich</MenuItem>
                            <MenuItem value={'DIVERS'}>divers</MenuItem>
                        </Select>
                    </FormControl>
                </Box>
            </Grid>
            <Grid container>
                <Grid size={12} sx={{mb: 2}}>
                    <TextField
                        id="firstname"
                        label="Vorname"
                        variant="outlined"
                        size="small"
                        value={firstname}
                        onChange={handleFirstname}
                        fullWidth
                    />
                </Grid>
                <Grid size={12} sx={{mb: 2}}>
                    <TextField
                        id="lastname"
                        label="Nachname"
                        variant="outlined"
                        size="small"
                        value={lastname}
                        onChange={handleLastname}
                        fullWidth
                    />
                </Grid>
            </Grid>
            {/* <Grid size={{xs: 12, sm: 9, md: 10}} sx={{mb: 1}}>
                <FormControl fullWidth>
                    <InputLabel id={'ingredients-label'} size="small">Straße</InputLabel>
                    <OutlinedInput
                        id="street"
                        label="Straße"
                        size="small"
                        value={ingredients}
                        onChange={handleIngredients}
                    />
                    <MyFormHelperText />
                </FormControl>
            </Grid> */}
            <Grid container>
                <Grid size={{xs: 12, sm: 9, md: 10}} sx={{mb: 2}}>
                    <TextField
                        id="street"
                        label="Straße"
                        variant="outlined"
                        size="small"
                        value={street}
                        onChange={handleStreet}
                        multiline
                        fullWidth
                    />
                </Grid>
                <Grid size={{xs: 12, sm: 3, md: 2}} sx={{mb: 2}}>
                    <TextField
                        id="homenumber"
                        label="Nr."
                        variant="outlined"
                        size="small"
                        value={homenumber}
                        onChange={handleHomenumber}
                        fullWidth
                    />
                </Grid>
            </Grid>
            <Grid container>
                <Grid size={{xs: 12, sm: 4, md: 5}} sx={{mb: 2}}>
                    <TextField
                        id="postalcode"
                        label="PLZ"
                        variant="outlined"
                        size="small"
                        value={postalcode}
                        onChange={handlePostalcode}
                        fullWidth
                    />
                </Grid>
                <Grid size={{xs: 12, sm: 8, md: 7}} sx={{mb: 2}}>
                    <TextField
                        id="domicile"
                        label="Wohnort"
                        variant="outlined"
                        size="small"
                        value={domicile}
                        onChange={handleDomicile}
                        multiline
                        fullWidth
                    />
                </Grid>
            </Grid>
            {/* <Button type={'submit'} variant={'outlined'} color={'success'}>Speichern</Button> */}
        </Grid>
    );
}