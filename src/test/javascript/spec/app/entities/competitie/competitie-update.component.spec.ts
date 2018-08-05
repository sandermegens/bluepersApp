/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BluepersAppTestModule } from '../../../test.module';
import { CompetitieUpdateComponent } from 'app/entities/competitie/competitie-update.component';
import { CompetitieService } from 'app/entities/competitie/competitie.service';
import { Competitie } from 'app/shared/model/competitie.model';

describe('Component Tests', () => {
    describe('Competitie Management Update Component', () => {
        let comp: CompetitieUpdateComponent;
        let fixture: ComponentFixture<CompetitieUpdateComponent>;
        let service: CompetitieService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BluepersAppTestModule],
                declarations: [CompetitieUpdateComponent]
            })
                .overrideTemplate(CompetitieUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CompetitieUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompetitieService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Competitie(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.competitie = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Competitie();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.competitie = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
