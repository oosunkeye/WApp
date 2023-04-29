describe("App Running Test", () => {
  it("passes", () => {
    cy.visit("http://localhost:3000/login");

    cy.location("pathname").should("include", "/login");

    cy.visit("http://localhost:3000/Signup");

    cy.location("pathname").should("include", "/Signup");

    cy.visit("http://localhost:3000/resetEmailLink");

    cy.location("pathname").should("include", "/resetEmailLink");
  });
});
