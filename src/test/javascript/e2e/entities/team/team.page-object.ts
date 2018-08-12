import { element, by, promise, ElementFinder } from 'protractor';

export class TeamComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-team div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TeamUpdatePage {
    pageTitle = element(by.id('jhi-team-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    naamInput = element(by.id('field_naam'));
    competitieSelect = element(by.id('field_competitie'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNaamInput(naam): promise.Promise<void> {
        return this.naamInput.sendKeys(naam);
    }

    getNaamInput() {
        return this.naamInput.getAttribute('value');
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
