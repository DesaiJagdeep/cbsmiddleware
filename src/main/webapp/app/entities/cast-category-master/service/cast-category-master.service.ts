import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICastCategoryMaster, NewCastCategoryMaster } from '../cast-category-master.model';

export type PartialUpdateCastCategoryMaster = Partial<ICastCategoryMaster> & Pick<ICastCategoryMaster, 'id'>;

export type EntityResponseType = HttpResponse<ICastCategoryMaster>;
export type EntityArrayResponseType = HttpResponse<ICastCategoryMaster[]>;

@Injectable({ providedIn: 'root' })
export class CastCategoryMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cast-category-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(castCategoryMaster: NewCastCategoryMaster): Observable<EntityResponseType> {
    return this.http.post<ICastCategoryMaster>(this.resourceUrl, castCategoryMaster, { observe: 'response' });
  }

  update(castCategoryMaster: ICastCategoryMaster): Observable<EntityResponseType> {
    return this.http.put<ICastCategoryMaster>(
      `${this.resourceUrl}/${this.getCastCategoryMasterIdentifier(castCategoryMaster)}`,
      castCategoryMaster,
      { observe: 'response' }
    );
  }

  partialUpdate(castCategoryMaster: PartialUpdateCastCategoryMaster): Observable<EntityResponseType> {
    return this.http.patch<ICastCategoryMaster>(
      `${this.resourceUrl}/${this.getCastCategoryMasterIdentifier(castCategoryMaster)}`,
      castCategoryMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICastCategoryMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICastCategoryMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCastCategoryMasterIdentifier(castCategoryMaster: Pick<ICastCategoryMaster, 'id'>): number {
    return castCategoryMaster.id;
  }

  compareCastCategoryMaster(o1: Pick<ICastCategoryMaster, 'id'> | null, o2: Pick<ICastCategoryMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getCastCategoryMasterIdentifier(o1) === this.getCastCategoryMasterIdentifier(o2) : o1 === o2;
  }

  addCastCategoryMasterToCollectionIfMissing<Type extends Pick<ICastCategoryMaster, 'id'>>(
    castCategoryMasterCollection: Type[],
    ...castCategoryMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const castCategoryMasters: Type[] = castCategoryMastersToCheck.filter(isPresent);
    if (castCategoryMasters.length > 0) {
      const castCategoryMasterCollectionIdentifiers = castCategoryMasterCollection.map(
        castCategoryMasterItem => this.getCastCategoryMasterIdentifier(castCategoryMasterItem)!
      );
      const castCategoryMastersToAdd = castCategoryMasters.filter(castCategoryMasterItem => {
        const castCategoryMasterIdentifier = this.getCastCategoryMasterIdentifier(castCategoryMasterItem);
        if (castCategoryMasterCollectionIdentifiers.includes(castCategoryMasterIdentifier)) {
          return false;
        }
        castCategoryMasterCollectionIdentifiers.push(castCategoryMasterIdentifier);
        return true;
      });
      return [...castCategoryMastersToAdd, ...castCategoryMasterCollection];
    }
    return castCategoryMasterCollection;
  }
}
