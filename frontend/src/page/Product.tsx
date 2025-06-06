import { useEffect, useState, type ChangeEvent } from "react";
import { useLocation, useParams, type Params } from "react-router-dom";
import axios from "axios";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import TextField from "@mui/material/TextField";

import type { IProduct } from "@/interface/IProduct";
import {colorItems} from "@data/colorData.ts";
import LayoutContainer from "@/component/share/LayoutContainer";
import { getMajuscule, getUnitIcon } from "@/util";
import Details from "@/component/Details";
import Price from "@/component/Price";
import PragraphContainer from "@/component/ParagraphContainer";
import AvatarCaption from "@/component/AvatarCaption";
import ButtonAction from "@/component/ButtonAction";
import Slider from "@/component/Slider";


export default function Product() {
    const id = useParams();
    const path = '/api/product/';
    const [product, setProduct] = useState<IProduct>();
    const location = useLocation();
    const stateProduct: IProduct = location.state?.product as IProduct;
    const [amount, setAmount] = useState<string>();
    const [total, setTotal] = useState<number>();

    useEffect(() => {
        if (stateProduct) {
            console.log(stateProduct);
            
            setProduct(stateProduct);
        } else {
            console.log(id);
            
            getProduct(id);
        };
    }, [id]);

    const getProduct = (id: Readonly<Params<string>>) => {
        axios.get(path + id.id).then(res => setProduct(res.data)).catch(err => console.log(err));
    }

    const handleAmount = (e: ChangeEvent<HTMLInputElement>) => {
        const amount = e.target.value;
        setAmount(amount);
        setTotal(product ? +amount * product.price : 0)
    };

    return (
        <LayoutContainer>
            {/* Image */}
            <Grid container spacing={6}>
                <Grid size={8}>
                    <Slider images={product?.images.large ?? []} />
                </Grid>
                <Grid size={4}>
                    {/* Manufacturer */}
                    <Typography component={'p'} sx={{fontSize: '.75rem'}}>{product?.manufacturer}</Typography>
                    {/* Name */}
                    <Typography component={'h1'} sx={{fontSize: '1.5rem', pb: '1rem'}}>{product?.name}</Typography>
                    {/* Dimension */}
                    <PragraphContainer>
                        <Typography component={'h3'} sx={{fontWeight: 600, pb: '.5rem'}}>Abmessungen</Typography>
                        {
                            product?.features.dimension && Object.entries(product.features.dimension).map(
                                (dimension) => (
                                    <Details 
                                        key={dimension[0]} 
                                        name={getMajuscule(dimension[0])} 
                                        value={dimension[1].number} 
                                        unit={getUnitIcon(dimension[1].unit).find(val => !!val)} 
                                    />
                                )
                            )
                        }
                    </PragraphContainer>
                    {/* Material */}
                    <PragraphContainer>
                        <Typography component={'h3'} sx={{fontWeight: 600, pb: '.5rem'}}>Material</Typography>
                        <Details name={
                            product?.features.materials.map(
                                material => {return getMajuscule(material)}
                                ).toString().replace(',', ', ') ?? ''
                            } 
                        />
                    </PragraphContainer>
                    {/* Farben */}
                    <PragraphContainer>
                        <Typography component={'h3'} sx={{fontWeight: 600, pb: '.75rem'}}>Farben</Typography>
                        <Stack direction="row" spacing={2}>
                            {product?.features.colors.map(color => {
                                const colorItem = colorItems.find(item => item.name === color);
                                return (
                                    colorItem && <AvatarCaption 
                                        key={colorItem.name}
                                        name={colorItem.name} 
                                        image={colorItem.image} 
                                        caption={colorItem.caption} 
                                        size={colorItem.size} 
                                        filter={colorItem.filter}
                                    />
                                )
                            })}
                        </Stack>
                    </PragraphContainer>
                    <br />
                    {/* Price */}
                    <PragraphContainer>
                        <Details 
                            name={'Preis'} 
                            value={<Price value={product?.price ?? ''} currency={product?.currency ?? ''} justify={'end'} />}
                        />
                    </PragraphContainer>
                    {/* Amount */}
                    <Details 
                        name={'Menge'} 
                        value={<TextField 
                            id="amount" 
                            variant="outlined" 
                            size="small" 
                            type={'number'} 
                            placeholder="0"
                            sx={{width: '3.5rem'}} 
                            slotProps={{
                                htmlInput: {
                                    sx: { px: '.4rem', py: '.25rem' },
                                    min: 0,
                                    max: 99
                                }
                            }} 
                            value={amount}
                            onChange={handleAmount}
                            required
                        />}
                    />
                    {/* Total */}
                    <PragraphContainer>
                        <Details 
                            name={'Gesamt'} 
                            value={<Price value={total ?? 0} currency={product?.currency ?? ''} justify={'end'} underline={true} />}
                        />
                    </PragraphContainer>
                    {/* Action */}
                    <Stack>
                        <ButtonAction value={'Kaufen'} color="success" click={() => {}} />
                    </Stack>
                </Grid>
                {/* Description */}
                <Grid size={12}>
                    <Typography component={'h2'} sx={{fontSize: '1.2rem', pb: '1rem'}}>Beschreibung</Typography>
                    <Typography component={'p'} sx={{fontWeight: 300}} gutterBottom>{product?.description}</Typography>
                </Grid>
            </Grid>
        </LayoutContainer>
    );
}