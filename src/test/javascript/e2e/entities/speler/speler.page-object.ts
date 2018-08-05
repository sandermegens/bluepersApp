import { element, by, promise, ElementFinder } from 'protractor';

export class SpelerComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-speler div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SpelerUpdatePage {
    pageTitle = element(by.id('jhi-speler-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    voornaamInput = element(by.id('field_voornaam'));
    tussenvoegselInput = element(by.id('field_tussenvoegsel'));
    achternaamInput = element(by.id('field_achternaam'));
    rugnummerInput = element(by.id('field_rugnummer'));
    positieSelect = element(by.id('field_positie'));
    geboorteDatumInput = element(by.id('field_geboorteDatum'));
    debuutInput = element(by.id('field_debuut'));
    bijzonderhedenInput = element(by.id('field_bijzonderheden'));
    wedstrijdSelect = element(by.id('field_wedstrijd'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setVoornaamInput(voornaam): promise.Promise<void> {
        return this.voornaamInput.sendKeys(voornaam);
    }

    getVoornaamInput() {
        return this.voornaamInput.getAttribute('value');
    }

    setTussenvoegselInput(tussenvoegsel): promise.Promise<void> {
        return this.tussenvoegselInput.sendKeys(tussenvoegsel);
    }

    getTussenvoegselInput() {
        return this.tussenvoegselInput.getAttribute('value');
    }

    setAchternaamInput(achternaam): promise.Promise<void> {
        return this.achternaamInput.sendKeys(achternaam);
    }

    getAchternaamInput() {
        return this.achternaamInput.getAttribute('value');
    }

    setRugnummerInput(rugnummer): promise.Promise<void> {
        return this.rugnummerInput.sendKeys(rugnummer);
    }

    getRugnummerInput() {
        return this.rugnummerInput.getAttribute('value');
    }

    setPositieSelect(positie): promise.Promise<void> {
        return this.positieSelect.sendKeys(positie);
    }

    getPositieSelect() {
        return this.positieSelect.element(by.css('option:checked')).getText();
    }

    positieSelectLastOption(): promise.Promise<void> {
        return this.positieSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    setGeboorteDatumInput(geboorteDatum): promise.Promise<void> {
        return this.geboorteDatumInput.sendKeys(geboorteDatum);
    }

    getGeboorteDatumInput() {
        return this.geboorteDatumInput.getAttribute('value');
    }

    setDebuutInput(debuut): promise.Promise<void> {
        return this.debuutInput.sendKeys(debuut);
    }

    getDebuutInput() {
        return this.debuutInput.getAttribute('value');
    }

    setBijzonderhedenInput(bijzonderheden): promise.Promise<void> {
        return this.bijzonderhedenInput.sendKeys(bijzonderheden);
    }

    getBijzonderhedenInput() {
        return this.bijzonderhedenInput.getAttribute('value');
    }

    wedstrijdSelectLastOption(): promise.Promise<void> {
        return this.wedstrijdSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    wedstrijdSelectOption(option): promise.Promise<void> {
        return this.wedstrijdSelect.sendKeys(option);
    }

    getWedstrijdSelect(): ElementFinder {
        return this.wedstrijdSelect;
    }

    getWedstrijdSelectedOption() {
        return this.wedstrijdSelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
