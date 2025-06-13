import { useState } from "react";
import { PayPalScriptProvider, PayPalButtons } from "@paypal/react-paypal-js";
import axios from "axios";
import type { OnApproveData , OnApproveActions } from "@paypal/paypal-js";

import type { ICart } from "@/interface/ICart";

type Message = {
    content: string;
};

// Renders errors or successfull transactions on the screen.
function Message({ content }: Readonly<Message>) {
    return <p>{content}</p>;
}

type Props = {
    cart: ICart[];
    onOrder: (paypalOrder: any) => void; 
};


export default function PayPal({cart, onOrder}: Readonly<Props>) {
    const initialOptions = {
        clientId: `${import.meta.env.PAYPAL_CLIENT_ID}`,
        enableFunding: "venmo",
        disableFunding: "",
        buyerCountry: "US",
        currency: "USD",
        dataPageType: "product-details",
        components: "buttons",
        dataSdkIntegrationSource: "developer-studio",
    };

    const [message, setMessage] = useState("");

    return (
        <div className="App">
            <PayPalScriptProvider options={initialOptions}>
                <PayPalButtons
                   style={{
                        shape: "rect",
                        layout: "vertical",
                        color: "gold",
                        label: "paypal",
                    }}
                   createOrder={async () => {
                        try {
                            const response = await axios.post("/api/orders", {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/json",
                                },
                                // use the "body" param to optionally pass additional order information
                                // like product ids and quantities
                                body: JSON.stringify({
                                    cart: cart.map(item => {
                                        return {
                                            id: item.id,
                                            quantity: `${item.amount}.00`
                                        }
                                    })
                                }),
                            });

                            const orderData = await response.data;

                            if (orderData.id) {
                                return orderData.id;
                            } else {
                                const errorDetail = orderData?.details?.[0];
                                const errorMessage = errorDetail
                                    ? `${errorDetail.issue} ${errorDetail.description} (${orderData.debug_id})`
                                    : JSON.stringify(orderData);

                                throw new Error(errorMessage);
                            }
                        } catch (error) {
                            console.error(error);
                            setMessage(
                                `Could not initiate PayPal Checkout...${error}`
                            );
                        }
                    }}
                   onApprove={
                        async (data: OnApproveData, actions: OnApproveActions) => {
                            try {
                                const response = await axios.post(
                                    `/api/orders/${data.orderID}/capture`,
                                    {
                                        method: "POST",
                                        headers: {
                                            "Content-Type": "application/json",
                                        }
                                    }
                                );

                                const orderData = await response.data;
                                // Three cases to handle:
                                //   (1) Recoverable INSTRUMENT_DECLINED -> call actions.restart()
                                //   (2) Other non-recoverable errors -> Show a failure message
                                //   (3) Successful transaction -> Show confirmation or thank you message

                                const errorDetail = orderData?.details?.[0];

                                if (errorDetail?.issue === "INSTRUMENT_DECLINED") {
                                    // (1) Recoverable INSTRUMENT_DECLINED -> call actions.restart()
                                    // recoverable state, per https://developer.paypal.com/docs/checkout/standard/customize/handle-funding-failures/
                                    return actions.restart();
                                } else if (errorDetail) {
                                    // (2) Other non-recoverable errors -> Show a failure message
                                    throw new Error(
                                        `${errorDetail.description} (${orderData.debug_id})`
                                    );
                                } else {
                                    // (3) Successful transaction -> Show confirmation or thank you message
                                    // Or go to another URL:  actions.redirect('thank_you.html');
                                    
                                    // Gets paypal order for posting to db.
                                    onOrder(orderData);
                                }
                            } catch (error) {
                                console.error(`Sorry, your transaction could not be processed...${error}`);
                            }
                        }
                   }
                />
            </PayPalScriptProvider>
            <Message content={message} />
        </div>
    );
}