import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Stack} from "@chakra-ui/react";
import {updateCustomer} from "../services/Client.js";
import {errorAlert, successAlert} from "../services/AlertToast.js";

const MyTextInput = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={3}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

// And now we can use these
const UpdateCustomerForm = ({fetchCustomers, initialValues, customerId}) => {
    return (
        <>
            <Formik
                initialValues={initialValues}
                validationSchema={Yup.object({
                    userName: Yup.string()
                        .min(6, 'Must be 6 characters or more')
                        .required('Required'),
                    firstName: Yup.string()
                        .min(3, 'Must be 3 characters or more')
                        .required('Required'),
                    lastName: Yup.string()
                        .min(3, 'Must be 3 characters or more')
                        .required('Required'),
                    email: Yup.string()
                        .email('Invalid email address')
                        .required('Required')
                        .lowercase(),
                    age: Yup.number()
                        .min(16, "Must be at least 16")
                        .max(100, "100 Years")
                        .required('Required')
                })}
                onSubmit={(updatedCustomer, {setSubmitting}) => {
                    setSubmitting(true);
                    updateCustomer(customerId, updatedCustomer)
                        .then(response => {
                            console.log(response);
                            successAlert(
                                "Customer Updated",
                                `Customer ${updatedCustomer.firstName} profile,  
                                 updated successfully`
                            )
                            fetchCustomers();
                        }).catch(error => {
                        console.log(error)
                        errorAlert(
                            error.code,
                            error.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false)
                    })
                }}
            >
                {({isValid, isSubmitting, dirty}) => (
                    <Form>
                        <Stack spacing={'20px'}>
                            <MyTextInput
                                label="User Name"
                                name="userName"
                                type="text"
                                placeholder="Jane.lopez"
                            />
                            <MyTextInput
                                label="First Name"
                                name="firstName"
                                type="text"
                                placeholder="Jane"
                            />
                            <MyTextInput
                                label="Last Name"
                                name="lastName"
                                type="text"
                                placeholder="Lopez"
                            />
                            <MyTextInput
                                label="Email Address"
                                name="email"
                                type="email"
                                placeholder="jane@codeNaren.com"
                            />
                            <MyTextInput
                                label="Age"
                                name="age"
                                type="number"
                                placeholder="16-100"
                            />
                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default UpdateCustomerForm;