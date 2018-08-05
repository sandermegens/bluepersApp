import { element, by, promise, ElementFinder } from 'protractor';

export class WedstrijdComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-wedstrijd div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class WedstrijdUpdatePage {
    pageTitle = element(by.id('jhi-wedstrijd-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    datumInput = element(by.id('field_datum'));
    tijdInput = element(by.id('field_tijd'));
    plaatsInput = element(by.id('field_plaats'));
    competitieSelect = element(by.id('field_competitie'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setDatumInput(datum): promise.Promise<void> {
        return this.datumInput.sendKeys(datum);
    }

    getDatumInput() {
        return this.datumInput.getAttribute('value');
    }

    setTijdInput(tijd): promise.Promise<void> {
        return this.tijdInput.sendKeys(tijd);
    }

    getTijdInput() {
        return this.tijdInput.getAttribute('value');
    }

    setPlaatsInput(plaats): promise.Promise<void> {
        return this.plaatsInput.sendKeys(plaats);
    }

    getPlaatsInput() {
        return this.plaatsInput.getAttribute('value');
    }

    competitieSelectLastOption(): promise.Promise<void> {
        return this.competitieSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    competitieSelectOption(option): promise.Promise<void> {
        return this.competitieSelect.sendKeys(option);
    }

    getCompetitieSelect(): ElementFinder {
        return this.competitieSelect;
    }

    getCompetitieSelectedOption() {
        return this.competitieSelect.element(by.css('option:checked')).getText();
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
