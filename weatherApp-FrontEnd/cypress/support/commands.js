const APP_URL = Cypress.env("appUrl");
const BACKEND_URL = Cypress.env("backendUrl");

Cypress.Commands.add("login", (credentials) => {
  cy.visit(`${APP_URL}/login`);

  cy.get("input[name=username]").type(credentials.username);
  cy.get("input[name=password]").type(credentials.password).type("{enter}");
});

Cypress.Commands.add("loginServerResponseCheck", (credentials) => {
  cy.request({
    method: "POST",
    url: `${BACKEND_URL}/user/login`,
    body: {
      username: credentials.username,
      password: credentials.password,
    },
  }).then((response) => {
    expect(response.status).to.eq(200);
  });
});

Cypress.Commands.add("InvalidloginServerResponseCheck", (credentials) => {
  cy.request({
    method: "POST",
    url: `${BACKEND_URL}/user/login`,
    body: {
      username: credentials.username,
      password: credentials.password,
    },
    failOnStatusCode: false,
  }).then((response) => {
    expect(response.status).to.eq(400);
    expect(response.body.message).to.eq("Username");
  });
});
